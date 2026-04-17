package br.ufal.ic.myfood.records;

import java.io.Serializable;
import java.util.Objects;

public class PairKey<A,B> implements Serializable {

    private A first;
    private B second;

    public PairKey() {
    }

    public PairKey(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PairKey<?, ?> pairKey)) return false;

        return Objects.equals(first, pairKey.first)
                && Objects.equals(second, pairKey.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
