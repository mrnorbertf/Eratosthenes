package com.reposh.fmgurbanov.hiretest.eratosthenes;

import java.util.BitSet;

/**
 * Created by Fedor on 04.09.2017.
 */

public interface ISieve {
    BitSet getPrimesUpTo(long limit);
}
