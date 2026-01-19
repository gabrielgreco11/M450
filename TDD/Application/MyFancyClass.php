<?php

class MyFancyClass
{
    /**
     * Shorten a string to a given length with an optional ending.
     * Returns false if the ending is longer than the length.
     */
    public function shortString($text, $length, $ending = '...') {
        if (!is_string($text)) {
            return false;
        }

        $textLength = mb_strlen($text);
        $endingLength = mb_strlen($ending);

        if ($length < $endingLength) {
            return false;
        }

        if ($textLength <= $length) {
            return $text;
        }

        return mb_substr($text, 0, $length - $endingLength) . $ending;
    }

    /**
     * Calculate the rounded average of an array of numbers.
     * Returns 0 if empty array.
     * Returns false if invalid values are found.
     */
    public function calcAverage($values) {
        if (!is_array($values) || empty($values)) {
            return 0;
        }

        $sum = 0;
        $count = 0;

        foreach ($values as $v) {
            if (!is_numeric($v)) {
                return false;
            }
            $sum += floatval($v);
            $count++;
        }

        return ($count === 0) ? 0 : round($sum / $count);
    }

    /**
     * Converts arrays to strings with a delimiter, or strings to arrays.
     * Returns false if input is not string or array.
     */
    public function getOpposite($value, $delimiter = ',') {
        if (is_array($value)) {
            return implode($delimiter, $value);
        } elseif (is_string($value)) {
            return explode($delimiter, $value);
        } else {
            return false;
        }
    }
}
