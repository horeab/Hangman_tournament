package libgdx.ui.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.StreamUtils;

import org.apache.commons.lang3.tuple.Pair;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import libgdx.game.Game;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.Res;
import libgdx.utils.InternetUtils;

public class ImageAsync {

    private Pair<Image, String>[] imageWithUrl;
    private Res defaultImage;

    public ImageAsync(Pair<Image, String>... imageWithUrl) {
        this.imageWithUrl = imageWithUrl;
    }

    public void create() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                processBeforeLoad();
                final List<PixmapInfo> pixmapInfos = new ArrayList<>();
                for (Pair<Image, String> imageWithUrl : imageWithUrl) {
                    byte[] bytes = new byte[200 * 1024]; // assuming the content is not bigger than 200kb.
                    int numBytes = downloadImage(bytes, imageWithUrl.getValue());
                    if (numBytes != 0) {
                        pixmapInfos.add(createPixmapInfo(new Pixmap(bytes, 0, numBytes)));
                    } else {
                        pixmapInfos.add(null);
                    }
                }
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (pixmapInfos.size() != imageWithUrl.length) {
                            throw new IllegalStateException("Error downloading images");
                        }
                        for (int i = 0; i < pixmapInfos.size(); i++) {
                            PixmapInfo pixmapInfo = pixmapInfos.get(i);
                            Drawable drawable = null;
                            if (pixmapInfo != null) {
                                drawable = getDrawable(pixmapInfo.getPixmap(), pixmapInfo.getOriginalWidth(), pixmapInfo.getOriginalHeight());
                            } else {
                                if (isLocalUrl(imageWithUrl[i].getValue())) {
                                    drawable = GraphicUtils.getImage(Game.getInstance().getMainDependencyManager().createResourceService().getByPath(imageWithUrl[i].getValue())).getDrawable();
                                } else if (defaultImage != null) {
                                    drawable = GraphicUtils.getImage(defaultImage).getDrawable();
                                }
                            }
                            imageWithUrl[i].getKey().setDrawable(drawable);
                        }
                        processAfterLoad();
                    }
                });
            }
        }).start();
    }

    public void setDefaultImage(Res defaultImage) {
        this.defaultImage = defaultImage;
    }

    public Pair<Image, String>[] getImageWithUrl() {
        return imageWithUrl;
    }

    private static TextureRegionDrawable getDrawable(Pixmap pixmap, int width, int height) {
        return new TextureRegionDrawable(new TextureRegion(new Texture(pixmap), 0, 0, width, height));
    }

    private int downloadImage(byte[] out, String url) {
        InputStream in = null;
        try {
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(true);
            conn.connect();
            in = conn.getInputStream();
            int readBytes = 0;
            while (true) {
                int length = in.read(out, readBytes, out.length - readBytes);
                if (length == -1) break;
                readBytes += length;
            }
            return readBytes;
        } catch (Exception ex) {
            return 0;
        } finally {
            StreamUtils.closeQuietly(in);
        }
    }

    private boolean isLocalUrl(String url) {
        return !InternetUtils.urlCanBeOpened(url);
    }

    private PixmapInfo createPixmapInfo(Pixmap originalPixmap) {
        int width = MathUtils.nextPowerOfTwo(originalPixmap.getWidth());
        int height = MathUtils.nextPowerOfTwo(originalPixmap.getHeight());
        Pixmap potPixmap = new Pixmap(width, height, originalPixmap.getFormat());
        potPixmap.drawPixmap(originalPixmap, 0, 0, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight());
        originalPixmap.dispose();
        return new PixmapInfo(potPixmap, originalPixmap.getWidth(), originalPixmap.getHeight());
    }

    private class PixmapInfo {

        private Pixmap pixmap;
        private int originalWidth;
        private int originalHeight;

        PixmapInfo(Pixmap pixmap, int originalWidth, int originalHeight) {
            this.pixmap = pixmap;
            this.originalWidth = originalWidth;
            this.originalHeight = originalHeight;
        }

        public Pixmap getPixmap() {
            return pixmap;
        }

        public int getOriginalWidth() {
            return originalWidth;
        }

        public int getOriginalHeight() {
            return originalHeight;
        }
    }

    //To override
    protected void processAfterLoad() {
    }

    //To override
    protected void processBeforeLoad() {
    }
}