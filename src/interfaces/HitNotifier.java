package interfaces;

/**@author rotem ghidale
 * This interface indicate that objects that implement it send notifications when they are being hit.*/
public interface HitNotifier {
    /**This functionAdd hl as a listener to hit events.
     * @param hl the hit listener*/
    void addHitListener(HitListener hl);

    /**This function Remove hl from the list of listeners to hit events.
     * @param hl the hit listener*/
    void removeHitListener(HitListener hl);
}