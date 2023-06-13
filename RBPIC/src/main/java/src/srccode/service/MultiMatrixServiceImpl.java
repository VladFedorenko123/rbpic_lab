package src.srccode.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultiMatrixServiceImpl implements MultiMatrixService {
    private final Random random;
    @Override
    public Integer multiMatrix(Integer rows, Integer columns) {
        int[][] matrix = generateMatrix(rows, columns);

        int result = 1;

        int numThreads = matrix.length;
        MatrixMultiplier[] threads = new MatrixMultiplier[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new MatrixMultiplier(matrix[i]);
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
                result *= threads[i].getResult();
            }
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
        }

        return result;
    }

    private int[][] generateMatrix(Integer rows, Integer columns){
        int[][] matrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextInt(9) + 1; // Генеруємо число від 1 до 9
            }
        }

        System.out.println("Двовимірний масив:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        return matrix;
    }

    private static class MatrixMultiplier extends Thread {
        private final int[] row;
        private int result;

        public MatrixMultiplier(int[] row) {
            this.row = row;
            this.result = 1;
        }

        public int getResult() {
            return result;
        }

        @Override
        public void run() {
            for (int j = 0; j < row.length; j++) {
                result *= row[j];
            }
        }
    }
}
