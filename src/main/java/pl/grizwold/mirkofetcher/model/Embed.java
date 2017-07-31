package pl.grizwold.mirkofetcher.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Embed {
    private boolean plus18;
    private EmbedType type;
    private String url;
    private String preview;
    private String source;
}
