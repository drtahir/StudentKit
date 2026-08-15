#!/bin/bash
echo "Fixing workflow..."
cat > .github/workflows/build.yml << 'WORKFLOW'
name: Build StudentKit APK

on:
  push:
    branches: [ main, master ]
  pull_request:
    branches: [ main, master ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v3

      - name: Decode keystore
        run: |
          echo "${{ secrets.KEYSTORE_BASE64 }}" | base64 --decode > studentkit-release.jks

      - name: Generate debug keystore
        run: |
          keytool -genkeypair \
            -keystore debug.keystore \
            -alias androiddebugkey \
            -keyalg RSA \
            -keysize 2048 \
            -validity 10000 \
            -storepass android \
            -keypass android \
            -dname "CN=Android Debug,O=Android,C=US"

      - name: Make gradlew executable
        run: chmod +x ./gradlew

      - name: Build Debug APK
        run: ./gradlew assembleDebug

      - name: Upload Debug APK
        uses: actions/upload-artifact@v4
        with:
          name: debug-apk
          path: app/build/outputs/apk/debug/app-debug.apk
          retention-days: 30

      - name: Build Release APK
        run: ./gradlew assembleRelease
        env:
          KEYSTORE_PATH: ${{ github.workspace }}/studentkit-release.jks
          STORE_PASSWORD: ${{ secrets.STORE_PASSWORD }}
          KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}
          KEY_ALIAS: ${{ secrets.KEY_ALIAS }}

      - name: Upload Release APK
        uses: actions/upload-artifact@v4
        with:
          name: release-apk
          path: app/build/outputs/apk/release/app-release.apk
          retention-days: 30
WORKFLOW

echo "Fixing Gradle version..."
sed -i 's/gradle-[0-9.]*-bin\.zip/gradle-9.3.1-bin.zip/' gradle/wrapper/gradle-wrapper.properties

echo "Restoring gradle-wrapper.jar if missing..."
if [ ! -f gradle/wrapper/gradle-wrapper.jar ]; then
  gradle wrapper --gradle-version 9.3.1
fi

echo "Removing Flutter files..."
rm -f pubspec.yaml pubspec.lock

echo "Committing..."
git add -A
git commit -m "Fix: restore Android build config after AI Studio update"
git push origin main
echo "Done!"
