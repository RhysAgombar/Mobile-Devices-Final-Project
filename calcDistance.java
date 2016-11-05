//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> <--- INCLUDE THIS IN MANIFEST

public double calcDistance(double longitude, double latitude){
	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
	Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	double currentLong = location.getLongitude();
	double currentLat= location.getLatitude();
	double dLong = longitude - currentLong;
	double dLat = latitude - currentLat;
	a = (sin(dLat/2))*(sin(dLat/2)) + cos(currentLat) * cos(latitude)* (sin(dLong/2))*(sin(dLong/2));
	c = 2*atan2(sqrt(a), sqrt(1-a));
	distance = 6373 * c;
	return distance;
}