package zyz.flutter.plugin.flutter_google_street_view;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaLink;

/**
 * Wrapper class to handle null location from Google Maps SDK.
 * Google Maps SDK may pass null when street view data is not available at a location.
 * This wrapper intercepts the call and provides a dummy location instead to avoid NPE.
 */
public class StreetViewPanoramaChangeListenerWrapper implements StreetViewPanorama.OnStreetViewPanoramaChangeListener {
    private final FlutterGoogleStreetView flutterGoogleStreetView;

    public StreetViewPanoramaChangeListenerWrapper(FlutterGoogleStreetView flutterGoogleStreetView) {
        this.flutterGoogleStreetView = flutterGoogleStreetView;
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation location) {
        if (location != null) {
            flutterGoogleStreetView.onStreetViewPanoramaChange(location);
        } else {
            // Create a dummy location with empty array for links and dummy LatLng to avoid NPE in Kotlin
            // The actual implementation will handle this as an error case
            StreetViewPanoramaLocation dummyLocation = new StreetViewPanoramaLocation(
                new StreetViewPanoramaLink[0], // links - empty array instead of null
                new LatLng(0, 0), // position - dummy LatLng instead of null
                null  // panoId
            );
            flutterGoogleStreetView.onStreetViewPanoramaChange(dummyLocation);
        }
    }
}
