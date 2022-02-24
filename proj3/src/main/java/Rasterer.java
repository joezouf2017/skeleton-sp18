import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlon = params.get("lrlon");
        double lrlat = params.get("lrlat");
        double w = params.get("w");
        if (ullon > lrlon || ullat < lrlat || lrlon < MapServer.ROOT_ULLON
                || ullon > MapServer.ROOT_LRLON || ullat < MapServer.ROOT_LRLAT
                || lrlat > MapServer.ROOT_ULLAT) {
            results.put("render_grid", new int[0][]);
            results.put("query_success", false);
            results.put("depth", 0);
            results.put("raster_ul_lon", Integer.MAX_VALUE);
            results.put("raster_ul_lat", Integer.MAX_VALUE);
            results.put("raster_lr_lon", Integer.MAX_VALUE);
            results.put("raster_lr_lat", Integer.MAX_VALUE);
            return results;
        }
        double londpp = (lrlon - ullon) / w;
        int depth = depth(londpp);
        int x1 = xcoord(ullon, depth);
        int x2 = xcoord(lrlon, depth);
        int y1 = ycoord(ullat, depth);
        int y2 = ycoord(lrlat, depth);
        double rullon = lon(x1, depth, false);
        double rullat = lat(y1, depth, false);
        double rlrlon = lon(x2, depth, true);
        double rlrlat = lat(y2, depth, true);
        String[][] grid = new String[y2 - y1 + 1][x2 - x1 + 1];
        for (int i = 0; i < y2 - y1 + 1; i++) {
            for (int j = 0; j < x2 - x1 + 1; j++) {
                grid[i][j] = "d" + depth + "_x" + (x1 + j) + "_y" + (y1 + i) + ".png";
            }
        }
        results.put("render_grid", grid);
        results.put("raster_ul_lon", rullon);
        results.put("raster_ul_lat", rullat);
        results.put("raster_lr_lon", rlrlon);
        results.put("raster_lr_lat", rlrlat);
        results.put("depth", depth);
        results.put("query_success", true);
        return results;
    }

    public static int depth(double londpp) {
        double d0 = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        int d = 0;
        while (londpp < d0) {
            d++;
            d0 *= 0.5;
            if (d == 7) {
                break;
            }
        }
        return d;
    }

    public static int xcoord(double lon, int depth) {
        int n = (int) Math.pow(2, depth);
        int result = 0;
        double interval = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / n;
        double start = MapServer.ROOT_ULLON;
        if (lon < start) {
            return result;
        }
        while (lon > start) {
            start += interval;
            result++;
            if (result == n) {
                break;
            }
        }
        result--;
        return result;
    }

    public static int ycoord(double lat, int depth) {
        int n = (int) Math.pow(2, depth);
        int result = 0;
        double interval = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / n;
        double start = MapServer.ROOT_ULLAT;
        if (lat > start) {
            return result;
        }
        while (lat < start) {
            start -= interval;
            result++;
            if (result == n) {
                break;
            }
        }
        result--;
        return result;
    }

    public static double lon(int x, int depth, boolean end) {
        int n = (int) Math.pow(2, depth);
        double interval = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / n;
        double result = MapServer.ROOT_ULLON;
        for (int i = 0; i < x; i++) {
            result += interval;
        }
        if (end) {
            result += interval;
        }
        return result;
    }

    public static double lat(int y, int depth, boolean end) {
        int n = (int) Math.pow(2, depth);
        double interval = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / n;
        double result = MapServer.ROOT_ULLAT;
        for (int i = 0; i < y; i++) {
            result -= interval;
        }
        if (end) {
            result -= interval;
        }
        return result;
    }
}
