package mware_lib;

// - Schnittstelle des Namensdienstes -
public abstract class NameService {
	
	// Meldet ein Objekt (servant) beim Namensdienst an.
	// Eine eventuell schon vorhandene Objektreferenz gleichen Namens
	// soll überschrieben werden.
	public abstract void rebind(Object servant, String name);

	// Liefert die Objektreferenz (Stellvertreterobjekt) zu einem Namen.
	public abstract Object resolve(String name);

}