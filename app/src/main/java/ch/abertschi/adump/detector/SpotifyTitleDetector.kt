package ch.abertschi.adump.detector

import ch.abertschi.adump.model.TrackRepository
import org.jetbrains.anko.AnkoLogger

/**
 * AdDetectable that checks for the Keyword Spotify
 *
 * Created by abertschi on 15.04.17.
 */
class SpotifyTitleDetector(val trackRepository: TrackRepository) : AbstractStatusBarDetector(), AnkoLogger {

    override fun canHandle(payload: AdPayload): Boolean {
        val title = getTitle(payload)
        if (title != null) {
            payload.ignoreKeys.add(title)
        }
        return super.canHandle(payload)
    }

    override fun flagAsAdvertisement(payload: AdPayload): Boolean {
        val title = getTitle(payload)
        return title != null && title.toLowerCase()
                .trim().contains("spotify")
    }

    fun getTitle(payload: AdPayload): String? {
        val notification = payload.statusbarNotification.notification
        if (notification != null && notification.tickerText != null) {
            return notification.tickerText.toString()
        }
        return ""
    }

    override fun flagAsMusic(payload: AdPayload): Boolean {
        val title = getTitle(payload)
        return title != null && trackRepository.getAllTracks().contains(title)
    }
}