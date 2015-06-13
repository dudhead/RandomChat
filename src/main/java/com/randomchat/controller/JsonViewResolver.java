package com.randomchat.controller;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/*
 * A hacked class that manages to render Spring's model as a JSON
 * 
 * When showModel=true is added to a URL, this class is used to convert
 * the model into a JSON output.
 * 
 * See Main.addInterceptors() for more information
 */
public class JsonViewResolver implements ViewResolver {
	
	public static final ViewResolver INSTANCE = new JsonViewResolver();
    
	/**
      * Get the view to use.
      *
      * @return Always returns an instance of {@link MappingJacksonJsonView}.
     */
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true); 
        return view;
    }
}