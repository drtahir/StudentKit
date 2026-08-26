# CRITICAL PROJECT RULES & ARCHITECTURE CONSTRAINTS

## PACKAGE STRUCTURE — NEVER CHANGE:
1. **Application ID**: `com.studentkit.buner`
2. **Source package**: `com.drtahir.studentkit`
3. **All Kotlin files live in**: `app/src/main/java/com/drtahir/studentkit/`
4. **NEVER** create files under `com/example/` — this causes duplicate class errors that break the entire build.
5. **NEVER** create a second `MainActivity.kt`, `StudentKitViewModel.kt`, or `Screen` sealed class — only ONE of each exists.

## WHEN ADDING NEW SCREENS:
1. Add new Screen objects to the EXISTING sealed class `Screen` in:
   `app/src/main/java/com/drtahir/studentkit/viewmodel/StudentKitViewModel.kt`
2. Add corresponding `when()` branches to the EXISTING `when(screen)` block in:
   `app/src/main/java/com/drtahir/studentkit/MainActivity.kt`
3. Never create a second sealed class `Screen` anywhere.
4. Never create a second `StudentKitViewModel` anywhere.

## WHEN ADDING NEW SCREENS OR FEATURES:
1. New screen files go in: `app/src/main/java/com/drtahir/studentkit/ui/screens/`
2. New data files go in: `app/src/main/java/com/drtahir/studentkit/data/`
3. New viewmodel files go in: `app/src/main/java/com/drtahir/studentkit/viewmodel/`
4. Every new Kotlin file must start with: `package com.drtahir.studentkit.[subfolder]`
5. Every import must use `com.drtahir.studentkit` NOT `com.example`.

## BUILD SYSTEM — NEVER TOUCH:
1. Never convert to Flutter — no `pubspec.yaml`, no `lib/` folder, no Dart files ever.
2. Never delete `gradle/wrapper/` folder or its contents.
3. Never change `gradle-wrapper.properties` `distributionUrl` — it must stay as `gradle-9.3.1-bin.zip`.
4. Never modify `.github/workflows/build.yml`.
5. Never change signing env variable names: `KEYSTORE_PATH`, `STORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`.
6. Never change `applicationId` from `com.studentkit.buner`.

## AFTER EVERY CHANGE:
1. List every file created or modified with full path.
2. List every file deleted.
3. Confirm no files were created under `com/example/`.
4. Confirm no duplicate class declarations exist.
5. Show this exact commit command to run in Termux:
   `git add -A && git commit -m "feat: [description]" && git push`

## QUALITY CHECKS BEFORE FINISHING:
1. Search output for any `com.example` references — if found, fix before displaying.
2. Search for duplicate function names across all files touched.
3. Confirm every new file has correct package declaration.
4. Confirm every import uses `com.drtahir.studentkit`.

## IF UNSURE ABOUT ANYTHING:
1. Ask before making the change.
2. Never guess at file locations or package names.
3. Never silently rename or move files.
