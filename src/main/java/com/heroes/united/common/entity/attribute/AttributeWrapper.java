package com.heroes.united.common.entity.attribute;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.entity.ai.attributes.IAttribute;
import java.util.Iterator;
import java.util.List;

public class AttributeWrapper {
    private final List<AttributePair> modifiers = Lists.newArrayList();
    public final IAttribute attribute;

    public AttributeWrapper(IAttribute attribute) {
        this.attribute = attribute;
    }

    protected List<Double> getModifiers(int operation) {
        List<Double> list = Lists.newArrayList();
        Iterator var3 = this.modifiers.iterator();

        while(var3.hasNext()) {
            AttributePair pair = (AttributePair)var3.next();
            if (operation == pair.operation) {
                list.add(pair.amount);
            }
        }

        return list;
    }

    public ImmutableList<AttributePair> getModifiers() {
        return ImmutableList.copyOf(this.modifiers);
    }

    public void apply(double amount, int operation) {
        this.modifiers.add(new AttributePair(amount, operation));
    }

    public boolean isEmpty() {
        return this.modifiers.isEmpty();
    }

    public double getValue(double baseValue) {
        List<Double> list = Lists.newArrayList(this.getModifiers(1));
        list.addAll(this.getModifiers(2));
        double amount;
        Iterator iter;

        for(iter = list.iterator(); iter.hasNext(); baseValue *= 1.0D + amount) {
            amount = (Double)iter.next();
        }

        for(iter = this.getModifiers(0).iterator(); iter.hasNext(); baseValue += amount) {
            amount = (Double)iter.next();
        }

        return this.attribute.clampValue(baseValue);
    }

    public float getValue(float baseValue) {
        return (float)this.getValue((double)baseValue);
    }
}
