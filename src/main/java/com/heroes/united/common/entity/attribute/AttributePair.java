package com.heroes.united.common.entity.attribute;

public class AttributePair {

    public final Double amount;
    public final Integer operation;

    public AttributePair(double amount, int operation) {
        this.amount = amount;
        this.operation = operation;
    }

    public String toString() {
        return String.format("AttributePair[amt=%s,op=%s]", this.amount, this.operation);
    }

    public int hashCode() {
        return this.amount.hashCode() ^ this.operation.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AttributePair)) {
            return false;
        } else {
            AttributePair pair = (AttributePair)obj;
            return pair.amount.equals(this.amount) && pair.operation.equals(this.operation);
        }
    }
}
