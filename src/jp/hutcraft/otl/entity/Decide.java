package jp.hutcraft.otl.entity;

import java.io.Serializable;

public final class Decide implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Decide NullDecide = new Decide(Field.NONE, "");
	private final Field field;
	private final String voice;
	public Decide(final Field field, final String voice) {
		this.field = field;
		this.voice = voice;
	}
	public Field getField() {
		return field;
	}
	public String getVoice() {
		return voice;
	}
}
