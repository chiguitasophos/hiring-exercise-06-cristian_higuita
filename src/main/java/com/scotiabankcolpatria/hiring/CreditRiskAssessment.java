package com.scotiabankcolpatria.hiring;

import java.util.Arrays;

/**
 * Clase que contiene los métodos para evaluar el riesgo de incumplimiento de nuevos clientes.
 * @Author: Cristian Higuita
 */
public class CreditRiskAssessment {
    
    /**
     * Mensaje de error cuando el arreglo de días de retraso en los pagos está vacío.
     */
    private static final String EMPTY_ARRAY_MESSAGE = "El arreglo de días de retraso en los pagos no puede estar vacío.";

    /**
     * Calcula la desviación estándar de un conjunto de datos.
     * @throws IllegalArgumentException si el arreglo de días de retraso en los pagos está vacío.
     * @param paymentDelays arreglo de enteros que representan los días de retraso en los pagos.
     * @return la desviación estándar de los días de retraso en los pagos.
     */
    public double standardDeviation(int[] paymentDelays) {
        int length = paymentDelays.length;
        double standardDeviation = 0.0;

        if (length == 0) {
            throw new IllegalArgumentException(EMPTY_ARRAY_MESSAGE);
        }

        double arithmeticMean = Arrays.stream(paymentDelays).average().getAsDouble();

        for (int num : paymentDelays) {
            standardDeviation += Math.pow(num - arithmeticMean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    /**
     * Busca el índice del pico máximo de retraso en los pagos.
     * @param paymentDelays arreglo de enteros que representan los días de retraso en los pagos.
     * @return el índice del pico máximo de retraso en los pagos.
     */
    public int paymentDelayMaxPeakIndex(int[] paymentDelays) {
        int index = -1;

        for (int i = 0; i < paymentDelays.length; i++) {
            if (paymentDelays[i] > index) {
                if (i == 0) {
                    index = paymentDelays[i] > paymentDelays[i + 1] ? i : index;
                } else if (i == paymentDelays.length - 1) {
                    index = paymentDelays[i] > paymentDelays[i - 1] ? i : index;
                } else {
                    index = paymentDelays[i] > paymentDelays[i + 1] && paymentDelays[i] > paymentDelays[i - 1] ? i
                            : index;
                }
            }
        }

        return index;
    }

    /**
     * Calcula la probabilidad de incumplimiento de pago por periodo.
     * @throws IllegalArgumentException si el arreglo de días de retraso en los pagos está vacío.
     * @param paymentDelays arreglo de dos dimensiones de enteros que representan los días de retraso en los pagos.
     * @return la probabilidad de incumplimiento de pago por periodo.
     */
    public double[] latePaymentProbabilityByPeriod(int[][] paymentDelays) {
        if (paymentDelays.length == 0) {
            throw new IllegalArgumentException(EMPTY_ARRAY_MESSAGE);
        }

        double[] latePaymentProbabilityByPeriod = new double[paymentDelays[0].length];
        for (int i = 0; i < paymentDelays.length; i++) {
            int[] paymentDelay = paymentDelays[i];
            for (int j = 0; j < paymentDelay.length; j++) {
                if (paymentDelay[j] > 0) {
                    latePaymentProbabilityByPeriod[j] += 1;
                }
            }
        }

        latePaymentProbabilityByPeriod = Arrays.stream(latePaymentProbabilityByPeriod)
                .map(i -> i / paymentDelays.length)
                .toArray();

        return latePaymentProbabilityByPeriod;
    }
}
