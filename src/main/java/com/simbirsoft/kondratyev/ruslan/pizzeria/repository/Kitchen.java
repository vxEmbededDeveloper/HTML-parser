package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

import com.simbirsoft.kondratyev.ruslan.pizzeria.HibernateUtil;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Kitchens;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipe;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipes;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import javax.persistence.TypedQuery;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_FORMATION;


public class Kitchen implements Kitchens<Ingredient> {
    private Integer sizePizza = 0;
    private Integer typeOfPizza = 0;
    private Integer currentPortion = 0;
    public static boolean readinessFlag = false;
    public static Integer maxPortionPizza = 0;
    public static Integer maxPortionIngredient = 0;

    public Kitchen(final Integer maxIngredient, final Integer maxPizza) {
        maxPortionIngredient = maxIngredient;
        maxPortionPizza = maxPizza;
        typeOfPizza = 1;
    }

    public Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd) {

        if (countToAdd == Dialog.ABORT){
            return WRONG_WASHOUT;
        }
        if (countToAdd > maxPortionIngredient){
            return WRONG_INPUT;
        }
        if ((countToAdd + currentPortion) > maxPortionPizza){
            return WRONG_FORMATION;
        }
        if (countToAdd == 0){
            return WRONG_NONE;
        }

        HibernateUtil.openSession();
        Recipe recipe = new Recipe();

        recipe.setCountIngredient(countToAdd);
        recipe.setRecipeNumber(typeOfPizza);
        recipe.setIngredients(ingredient);

        HibernateUtil.getSession().persist(recipe);

        HibernateUtil.commitSession();

        currentPortion += countToAdd;
        if (currentPortion.equals(maxPortionPizza)){
            readinessFlag = true;
        }
        return WRONG_NONE;
    }

    public Wrongs setSizePizza(Integer sizePizza) {
        if (sizePizza == Dialog.ABORT){
            return WRONG_WASHOUT;
        }
        this.sizePizza = sizePizza;
        return WRONG_NONE;
    }

    public Collection<String> getPizza() {
        List<String> pizza = new ArrayList<>();
        pizza.add("Размер пиццы: " + sizePizza);

        HibernateUtil.openSession();

        TypedQuery<Recipe> queryType = HibernateUtil.getSession().createNamedQuery(Recipes.getRecipe,Recipe.class);
        queryType.setParameter("serialNumber",typeOfPizza);
        List<Recipe> recipes = queryType.getResultList();

        for(Recipe recipe: recipes){
            pizza.add(recipe.getIngredients().getName());
        }

        HibernateUtil.commitSession();
        readinessFlag = true;
        typeOfPizza++;
        return pizza;
    }

    public void restartKitchen() {
        sizePizza = 0;
        currentPortion = 0;
        readinessFlag = false;
    }
}