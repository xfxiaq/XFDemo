package com.xfhy.common.dto;

public class TextValueDTO {

		String text="";
		
		String id = "";

		public TextValueDTO(String text,String id){
			this.text = text;
			this.id = id;
		}
		
		public TextValueDTO(){
			
		}
		
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	
}
