package org.araymond.joal.core.client.emulated.generator.key;

import com.turn.ttorrent.common.protocol.TrackerMessage.AnnounceRequestMessage.RequestEvent;
import org.araymond.joal.core.client.emulated.TorrentClientConfigIntegrityException;
import org.araymond.joal.core.client.emulated.generator.peerid.type.PeerIdTypes;
import org.araymond.joal.core.ttorent.client.MockedTorrent;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by raymo on 16/07/2017.
 */
public class KeyGeneratorTest {

    public static KeyGenerator createDefault() {
        return new NeverRefreshKeyGenerator(8, PeerIdTypes.ALPHABETIC, false, false);
    }

    @Test
    public void shouldNotBuildWithLengthLessThanOne() {
        assertThatThrownBy(() -> new DefaultKeyGenerator(0, PeerIdTypes.ALPHANUMERIC, false, false))
                .isInstanceOf(TorrentClientConfigIntegrityException.class)
                .hasMessage("key length must be greater than 0.");
    }

    @Test
    public void shouldNotBuildWithNullTypeLessThanOne() {
        assertThatThrownBy(() -> new DefaultKeyGenerator(8, null, false, false))
                .isInstanceOf(TorrentClientConfigIntegrityException.class)
                .hasMessage("key type must not be null.");
    }


    @Test
    public void shouldGenerateKeyLowerCased() {
        final KeyGenerator generator = new DefaultKeyGenerator(8, PeerIdTypes.ALPHABETIC, false, true);

        for (int i = 0; i < 30; i++) {
            assertThat(generator.generateKey()).matches("[a-z]{8}");
        }
    }

    @Test
    public void shouldGenerateKeyUpperCased() {
        final KeyGenerator generator = new DefaultKeyGenerator(8, PeerIdTypes.ALPHABETIC, true, false);

        for (int i = 0; i < 30; i++) {
            assertThat(generator.generateKey()).matches("[A-Z]{8}");
        }
    }

    private static final class DefaultKeyGenerator extends KeyGenerator {

        private DefaultKeyGenerator(final Integer length, final PeerIdTypes type, final boolean upperCase, final boolean lowerCase) {
            super(length, type, upperCase, lowerCase);
        }

        @Override
        public String getKey(final MockedTorrent torrent, final RequestEvent event) {
            return "";
        }
    }

}
