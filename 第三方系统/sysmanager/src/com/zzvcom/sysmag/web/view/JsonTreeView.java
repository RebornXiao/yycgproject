package com.zzvcom.sysmag.web.view;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;

import com.zzvcom.sysmag.util.JSONConverter;

public class JsonTreeView extends AbstractView {
	
	private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json";
	
	public JsonTreeView() {
		super();
		setContentType(DEFAULT_CONTENT_TYPE);
	}
	
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(getContentType());
		if (model.get("excluded") == null) {
			response.getWriter().write(JSONConverter.getInstance().marshall(model.get("tree"), null).toString());
		} else {
			response.getWriter().write(JSONConverter.getInstance().marshall(model.get("tree"), (String)model.get("excluded")).toString());
		}
		
		response.getWriter().close();
	}
	

}
