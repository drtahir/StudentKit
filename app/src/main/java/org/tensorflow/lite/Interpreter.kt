package org.tensorflow.lite

import java.nio.ByteBuffer

class Interpreter(modelBuffer: ByteBuffer, options: Options?) {
    class Options {
        fun setNumThreads(numThreads: Int) {}
    }

    fun run(input: Any, output: Any) {
        // Mock execution
    }

    fun close() {
        // Mock close
    }
}
