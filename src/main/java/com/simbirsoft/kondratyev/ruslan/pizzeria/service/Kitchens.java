package com.simbirsoft.kondratyev.ruslan.pizzeria.service;

import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.PizzaDto;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.Collection;

public interface Kitchens<IngredientDto> {
    Wrongs addToRecipe(IngredientDto ingredientDto, Integer countToAdd);
    PizzaDto getPizza();
}