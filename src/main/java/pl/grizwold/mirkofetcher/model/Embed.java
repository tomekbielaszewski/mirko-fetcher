package pl.grizwold.mirkofetcher.model;

import lombok.Data;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class Embed {
    private boolean plus18;
    private EmbedType type;
    private String url;
}
