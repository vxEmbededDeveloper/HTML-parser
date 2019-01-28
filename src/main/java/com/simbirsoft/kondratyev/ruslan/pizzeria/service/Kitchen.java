package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.enums.Wrongs.*;
import com.simbirsoft.kondratyev.ruslan.pizzeria.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.*;

public class Kitchen {
    public Kitchen(Integer maxPortionIngredient,Integer maxPortionPizza){
        this.maxPortionIngredient = maxPortionIngredient;
        this.maxPortionPizza = maxPortionPizza;
    }

    public Wrongs addToPecipe(Ingredient ingredient, Integer countToAdd, Integer totalCount){
        if(countToAdd == Dialog.ABORT) return WRONG_WASHOUT;
        if(countToAdd > maxPortionIngredient) return WRONG_INPUT;
        if((countToAdd + currentPortion) > maxPortionPizza) return WRONG_FORMATION;
        if(countToAdd == 0) return WRONG_NONE;
        recipe.put(ingredient,countToAdd);
        currentPortion += countToAdd;
        if(currentPortion == maxPortionPizza) readinessFlag = true;
        return WRONG_NONE;
    }
    public void restartKitchen(){
        sizePizza = 0;
        currentPortion = 0;
        readinessFlag = false;
        recipe.clear();
    }
    public Wrongs setSizePizza(Integer sizePizza) {
        if(sizePizza == Dialog.ABORT) return WRONG_WASHOUT;
        this.sizePizza = sizePizza;
        return WRONG_NONE;
    }
    public Collection<String> getPizza(){
        List<String> pizza = new ArrayList<>();
        pizza.add("Размер пиццы: " + sizePizza);
        for (Map.Entry pair:recipe.entrySet())
            pizza.add(pair.getKey() + "->" + pair.getValue() + " пр.");
        readinessFlag = true;
        return pizza;
    }

    private Map<Ingredient,Integer> recipe = new HashMap<>();
    public static Integer maxPortionPizza = 0;
    private Integer sizePizza = 0;
    private Integer currentPortion = 0;
    public static Integer maxPortionIngredient = 0;
    public static boolean readinessFlag = false;

}