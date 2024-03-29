package org.schabi.newpipe.extractor.services.bilibili;

import java.util.Random;

import javax.annotation.Nonnull;

public class DeviceForger {

    static public class Device {
        private String userAgent;

        private String webGlVersion;
        private String webGlVersionBase64;

        private String webGLRendererInfo;
        private String webGLRendererInfoBase64;

        public String getUserAgent() {
            return userAgent;
        }

        void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public String getWebGlVersion() {
            return webGlVersion;
        }

        public String getWebGlVersionBase64() {
            return webGlVersionBase64;
        }

        void setWebGlVersion(String webGlVersion) {
            this.webGlVersion = webGlVersion;
            this.webGlVersionBase64 = utils.encodeToBase64SubString(webGlVersion);
        }

        public String getWebGLRendererInfo() {
            return webGLRendererInfo;
        }

        public String getWebGLRendererInfoBase64() {
            return webGLRendererInfoBase64;
        }

        void setWebGLRendererInfo(String webGLRendererInfo) {
            this.webGLRendererInfo = webGLRendererInfo;
            this.webGLRendererInfoBase64 = utils.encodeToBase64SubString(webGLRendererInfo);
        }

        public Device(String userAgent, String webGlVersion, String webGLRendererInfo) {
            this.userAgent = userAgent;
            this.webGlVersion = webGlVersion;
            this.webGlVersionBase64 = utils.encodeToBase64SubString(webGlVersion);
            this.webGLRendererInfo = webGLRendererInfo;
            this.webGLRendererInfoBase64 = utils.encodeToBase64SubString(webGLRendererInfo);
        }

        public String info() {
            return "{"
                    + "UserAgent: " + getUserAgent()
                    + ", WebGlVersion: " + getWebGlVersion()
                    + ", WebGLRendererInfo: " + getWebGLRendererInfo()
                    + "}";
        }
    }

    static class GraphicCard {
        private final String vendor;
        private final String model;

        public GraphicCard(String vendor, String model) {
            this.vendor = vendor;
            this.model = model;
        }

        public String getVendor() {
            return vendor;
        }

        public String getModel() {
            return model;
        }
    }

    final static String UserAgentTemplate = "Mozilla/5.0 (%s) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/%d.0.0.0 Safari/537.36";
    final static String DefaultChromiumWebGlVersion = "WebGL 1.0 (OpenGL ES 2.0 Chromium)";
    final static String ChromiumAngleRendererInfoTemplate = "ANGLE (%s, %s Direct3D11 vs_5_0 ps_5_0, D3D11)Google Inc. (%s)";

    static String buildUserAgent(String platform, int version) {
        return String.format(UserAgentTemplate, platform, version);
    }

    static String buildChromiumAngleRendererInfo(String vendor, String gpuWithApi) {
        return String.format(ChromiumAngleRendererInfoTemplate, vendor, gpuWithApi, vendor);
    }

    static GraphicCard randomCard(Random random) {
        GraphicCard[] cards = {
                new GraphicCard("NVIDIA", "NVIDIA GeForce MX150 (0x00001D12)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce MX250 (0x00001D13)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce MX350 (0x00001C94)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce MX450 (0x00001F97)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTS 250 (0x00000615)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 670 (0x00001189)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 750 Ti (0x00001380)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GT 1030 (0x00001D01)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1050 (0x00001C81)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1050 Ti (0x00001C82)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1050 Ti (0x00001C8C)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1060 3GB (0x00001C02)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1060 6GB (0x00001C03)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1060 Ti (0x00001380)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1070 (0x00001B81)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1080 (0x00001B80)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1650 (0x00001F82)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1660 Ti (0x00002182)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce GTX 1660 SUPER (0x000021C4)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce RTX 2060 (0x00001F15)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce RTX 2060 (0x00001E89)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce RTX 3050 (0x00002507)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce RTX 3050 Ti Laptop GPU (0x000025E0)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce RTX 3060 Ti (0x000024C9)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce RTX 3060 Laptop GPU (0x00002520)"),
                new GraphicCard("NVIDIA", "NVIDIA GeForce RTX 4090 (0x00002684)"),
                new GraphicCard("NVIDIA", "NVIDIA Quadro P620 (0x00001CB6)"),
                new GraphicCard("NVIDIA", "NVIDIA Quadro P1000 (0x00001CB1)"),
                new GraphicCard("NVIDIA", "NVIDIA Quadro M4000 (0x000013F1)"),
                new GraphicCard("NVIDIA", "NVIDIA Quadro K1000M (0x00000FFC)"),
                new GraphicCard("Intel", "Intel(R) HD Graphics 630 (0x0000591B)"),
                new GraphicCard("Intel", "Intel(R) HD Graphics 4600 (0x00000416)"),
                new GraphicCard("Intel", "Intel(R) HD Graphics Family (0x00000A16)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics (0x000046A3)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics (0x000046D2)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics (0x00004E55)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics (0x00009BC4)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics (0x0000A720)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics 620 (0x00005917)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics 630 (0x0000591B)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics 630 (0x00003E9B)"),
                new GraphicCard("Intel", "Intel(R) UHD Graphics 770 (0x0000A780)"),
                new GraphicCard("Intel", "Intel(R) Iris(R) Xe Graphics (0x000046A6)"),
                new GraphicCard("Intel", "Intel(R) Iris(R) Xe Graphics (0x00009A49)"),
                new GraphicCard("Intel", "Intel(R) Iris(R) Xe Graphics (0x0000A7A0)"),
                new GraphicCard("AMD", "AMD Radeon(TM) Graphics (0x00001636)"),
                new GraphicCard("AMD", "AMD Radeon(TM) Graphics (0x0000164C)"),
                new GraphicCard("AMD", "AMD Radeon(TM) Graphics (0x00001638)"),
                new GraphicCard("AMD", "AMD Radeon(TM) Graphics (0x00001681)"),
                new GraphicCard("AMD", "AMD Radeon(TM) Graphics (0x0000679A)"),
                new GraphicCard("AMD", "AMD Radeon(TM) R7 Graphics (0x00001313)"),
                new GraphicCard("AMD", "AMD Radeon(TM) Vega 8 Graphics (0x000015D8)"),
                new GraphicCard("AMD", "AMD Radeon(TM) Vega 8 Graphics (0x000015DD)"),
                new GraphicCard("AMD", "AMD Radeon 780M Graphics (0x000015BF)"),
                new GraphicCard("AMD", "AMD Radeon RX 580 2048SP (0x00006FDF)"),
                new GraphicCard("AMD", "AMD Radeon RX 5700 XT (0x0000731F)"),
                new GraphicCard("AMD", "AMD Radeon RX 6400 (0x0000743F)"),
                new GraphicCard("AMD", "AMD Radeon RX 6650 XT (0x000073EF)"),
                new GraphicCard("AMD", "AMD Radeon RX 7900 XTX (0x0000744C)"),
                new GraphicCard("AMD", "Radeon Pro 555X (0x000067EF)"),
                new GraphicCard("AMD", "Radeon RX550/550 Series (0x0000699F)"),
                new GraphicCard("AMD", "Radeon RX 570 Series (0x000067DF)"),
                new GraphicCard("Unknown", "Mi Pad 5 Adreno 640 GPU"),
                new GraphicCard("Unknown", "Qualcomm(R) Adreno(TM) 8cx Gen 3"),
        };
        return cards[random.nextInt(cards.length)];
    }

    static Device forgeDevice(Random random) {
        // currently we only forge device with
        // * Modern Chrome Browse (so User Agent are frozen)
        // * Windows 10/11 X64 Operate System
        int chromiumVersion = (random.nextInt(7)) + 115;
        GraphicCard graphicCard = randomCard(random);
        Device device = new Device(
                buildUserAgent("Windows NT 10.0; Win64; x64", chromiumVersion),
                DefaultChromiumWebGlVersion,
                buildChromiumAngleRendererInfo(graphicCard.getVendor(), graphicCard.getModel())
        );
        return device;
    }

    static private Device currentDevice = null;

    @Nonnull
    static public Device requireRandomDevice() {
        if (currentDevice == null) {
            regenerateRandomDevice();
        }
        return currentDevice;
    }

    static public void regenerateRandomDevice() {
        currentDevice = forgeDevice(new Random(System.currentTimeMillis()));
    }

}
