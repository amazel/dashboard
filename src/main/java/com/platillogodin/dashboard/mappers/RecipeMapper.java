package com.platillogodin.dashboard.mappers;

import com.platillogodin.dashboard.commands.RecipeCommand;
import com.platillogodin.dashboard.domain.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Hugo Lezama on August - 2018
 */

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeCommand recipeToCommand (Recipe recipe);
    Recipe commandToRecipe (RecipeCommand recipeCommand);
}
