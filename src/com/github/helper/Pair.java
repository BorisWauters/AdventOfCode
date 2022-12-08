package com.github.helper;

import java.util.Objects;

class Pair<A, B> {
    private final A a;
    private final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Pair<?, ?>)) {
            return false;
        }
        Pair<?, ?> o = (Pair<?,?>) other;
        return a.equals(o.a) && b.equals(o.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}