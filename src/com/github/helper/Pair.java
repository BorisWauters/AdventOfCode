package com.github.helper;

import java.util.Objects;

class Pair<A, B> {
    private A a;
    private B b;

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

    public void setA(A a) {
        this.a = a;;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return a + ", " + b;
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