#!/usr/bin/env python3
import json
import os

# We will generate 6 Kotlin files with 180, 180, 180, 170, 170, 170 questions (Total = 1050 questions)

def escape_kt_str(s):
    # Escape quotes and backslashes and dollar signs
    return s.replace('\\', '\\\\').replace('"', '\\"').replace('$', '\\$')

def write_kotlin_part(file_path, class_name, method_name, subject_name, questions):
    os.makedirs(os.path.dirname(file_path), exist_ok=True)
    with open(file_path, "w", encoding="utf-8") as f:
        f.write("package com.example.ui.screens\n\n")
        f.write("/**\n")
        f.write(f" * MOAVINEEN-E-HUJJAJ QUESTION BANK - {class_name}\n")
        f.write(f" * Subject: {subject_name} ({len(questions)} Unique MCQs)\n")
        f.write(" */\n")
        f.write(f"object {class_name} {{\n\n")
        f.write(f"    fun {method_name}(startId: Int): List<MoavineenQuestion> {{\n")
        f.write("        val list = ArrayList<MoavineenQuestion>(" + str(len(questions)) + ")\n")
        f.write("        var idCounter = startId\n\n")
        
        # Batch by helper to avoid huge single method bytecode if needed, or direct list additions
        # In Kotlin, splitting into private helper funs if list is > 100 prevents 64KB method limit!
        chunk_size = 40
        chunks = [questions[i:i + chunk_size] for i in range(0, len(questions), chunk_size)]
        
        for idx, chunk in enumerate(chunks):
            f.write(f"        populateBatch{idx + 1}(list, idCounter)\n")
            f.write(f"        idCounter += {len(chunk)}\n")
        
        f.write("\n        return list\n")
        f.write("    }\n\n")
        
        # Write batch functions
        for idx, chunk in enumerate(chunks):
            f.write(f"    private fun populateBatch{idx + 1}(list: MutableList<MoavineenQuestion>, startId: Int) {{\n")
            f.write("        var idCounter = startId\n")
            for q in chunk:
                pos = escape_kt_str(q["pos"])
                subj = escape_kt_str(q["subj"])
                ques = escape_kt_str(q["question"])
                opts = [f'"{escape_kt_str(opt)}"' for opt in q["options"]]
                opts_str = ", ".join(opts)
                corr = q["correctIndex"]
                exp = escape_kt_str(q["explanation"])
                ref = escape_kt_str(q["reference"])
                
                f.write("        list.add(\n")
                f.write("            MoavineenQuestion(\n")
                f.write("                id = idCounter++,\n")
                f.write(f'                positionTarget = "{pos}",\n')
                f.write(f'                subjectCategory = "{subj}",\n')
                f.write(f'                question = "{ques}",\n')
                f.write(f'                options = listOf({opts_str}),\n')
                f.write(f'                correctIndex = {corr},\n')
                f.write(f'                explanation = "{exp}",\n')
                f.write(f'                reference = "{ref}"\n')
                f.write("            )\n")
                f.write("        )\n")
            f.write("    }\n\n")
            
        f.write("}\n")
    print(f"Wrote {len(questions)} MCQs to {file_path}")

print("Helper script initialized.")
