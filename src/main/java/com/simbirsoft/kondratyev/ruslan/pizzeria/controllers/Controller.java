package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Suggest.INGREDIENT;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Suggest.SIZE;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

public class Controller {
    private StoreHouse storeHouse;
    private Dialog dialog;
    private Kitchen kitchen;
    private Wrongs globalWrongs;

    public Controller(final StoreHouse storeHouse, final Dialog dialog, final Kitchen kitchen){
        this.storeHouse = storeHouse;
        this.dialog = dialog;
        this.kitchen = kitchen;
    }
    public void exec(){
        boolean status = dialog.welcome();
        Integer countIngredientStore = 0;
        Integer countIngredientUser = 0;
        Collection<Ingredient> ingredients = storeHouse.getAllIngredients();

        while (status) {
            kitchen.restartKitchen();
            while (!Kitchen.readinessFlag) {
                dialog.wish();
                globalWrongs = kitchen.setSizePizza(dialog.suggest(SIZE, null));
                if (globalWrongs == WRONG_WASHOUT){
                    break;
                }
                for (Ingredient ingredient : ingredients) {
                    countIngredientStore = storeHouse.getQuantity(ingredient);
                    if (countIngredientStore == 0) {
                        continue;
                    }
                    while (true) {
                        countIngredientUser = dialog.suggest(INGREDIENT, new Pair<>(ingredient, countIngredientStore));
                        globalWrongs = storeHouse.getIngredient(ingredient,countIngredientUser);
                        if (globalWrongs == WRONG_NONE){
                            globalWrongs = kitchen.addToPecipe(ingredient, countIngredientUser);
                        }
                        if (globalWrongs == WRONG_INPUT || globalWrongs == WRONG_FORMATION) {
                            dialog.wrongMessage(globalWrongs);
                        }
                        if (globalWrongs == WRONG_NONE || globalWrongs == WRONG_WASHOUT){
                            break;
                        }
                    }
                    if (globalWrongs == WRONG_WASHOUT || Kitchen.readinessFlag) {
                        break;
                    }
                }
                if (globalWrongs == WRONG_WASHOUT) {
                    storeHouse.restoreStore();
                    break;
                }
                dialog.orderFormed();
                try {
                    dialog.issueResult(kitchen.getPizza());
                } catch(Exception err) {
                    System.out.println(err.getMessage());
                }
            }
            status = dialog.repeat();
        }
        dialog.goodbuy();
    }
}
