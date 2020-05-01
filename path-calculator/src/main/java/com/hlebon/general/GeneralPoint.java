package com.hlebon.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class GeneralPoint<T extends GeneralPoint<T>> {
	protected final T previousPoint;
	protected final double price;
}
