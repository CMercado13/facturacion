package com.factura.facturacion.utilidades;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Utilidades {

	public boolean dateMayorNow(long milis) {
		Date d = new Date(milis);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		System.out.println(calendar.getTime());
		System.out.println(d);
		return calendar.getTime().after(d);
	}

}
