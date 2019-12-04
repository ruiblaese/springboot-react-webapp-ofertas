package com.cdpu.util.enums;

public enum TypeDeal {	
	
	LOCAL(1), PRODUTO(2), VIAGEM(3);

	private final int value;

    private TypeDeal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}