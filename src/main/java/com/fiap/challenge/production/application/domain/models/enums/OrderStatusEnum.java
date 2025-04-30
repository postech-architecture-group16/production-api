package com.fiap.challenge.production.application.domain.models.enums;

public enum OrderStatusEnum {
	RECEBIDO("RECEBIDO"),
	EM_PREPARACAO("EM_PREPARACAO"),
	PRONTO("PRONTO"),
	FINALIZADO("FINALIZADO");
	
	private String text;

	OrderStatusEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static OrderStatusEnum fromString(String text) {
		for (OrderStatusEnum b : OrderStatusEnum.values()) {
			if (b.text.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}
}
