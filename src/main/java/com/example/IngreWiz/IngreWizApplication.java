package com.example.IngreWiz;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Recipe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class IngreWizApplication {

	public static void main(String[] args) {
		//SpringApplication.run(IngreWizApplication.class, args);
		List<String> ing1 = new ArrayList<>(Arrays.asList("tomato", "potato"));
		List<String> stp1 = new ArrayList<>(Arrays.asList("Prepare 400g tomatoes and 300g potatoes", "peel them", "boil them in water"));
		List<String> ph1 = new ArrayList<>(Arrays.asList("photoURL1"));

		Recipe r1 = new Recipe("recipe1", Category.ITALIAN, 2, "test recipe 1", ing1, stp1, ph1);

		List<Recipe> savedR = new ArrayList<>();
		Chef c1 = new Chef("xy", Category.CHINESE, savedR);



	}
}
