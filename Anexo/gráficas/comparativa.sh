#!/bin/sh

java tia.Main -d ../../small_faces_database -t 0.1 -T 1000 -c 100 >resultados.txt
java tia.Main -d ../../small_faces_database -t 0.1 -T 1500 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.1 -T 2000 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.1 -T 5000 -c 100 >>resultados.txt

java tia.Main -d ../../small_faces_database -t 0.2 -T 1000 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.2 -T 1500 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.2 -T 2000 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.2 -T 5000 -c 100 >>resultados.txt

java tia.Main -d ../../small_faces_database -t 0.3 -T 1000 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.3 -T 1500 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.3 -T 2000 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.3 -T 5000 -c 100 >>resultados.txt

java tia.Main -d ../../small_faces_database -t 0.5 -T 1000 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.5 -T 1500 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.5 -T 2000 -c 100 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.5 -T 5000 -c 100 >>resultados.txt

java tia.Main -d ../../small_faces_database -t 0.1 -T 1000 -c 1000 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.1 -T 1500 -c 1000 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.1 -T 2000 -c 1000 >>resultados.txt
java tia.Main -d ../../small_faces_database -t 0.1 -T 5000 -c 1000 >>resultados.txt

