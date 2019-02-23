package top.codemaster.mifinder.data;

import java.util.Objects;

public class Resource {

    /**
     * 资源ID
     */
    private String id;

    /**
     * 资源类型
     */
    private ResType resType;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源缩略图
     */
    private String thumbnail;


    /**
     * 资源类型
     */
    public enum ResType {
        image,
        audio,
        video
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResType getResType() {
        return resType;
    }

    public void setResType(ResType resType) {
        this.resType = resType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id) &&
                resType == resource.resType &&
                Objects.equals(name, resource.name) &&
                Objects.equals(thumbnail, resource.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resType, name, thumbnail);
    }
}
