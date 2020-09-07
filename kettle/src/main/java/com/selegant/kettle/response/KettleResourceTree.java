package com.selegant.kettle.response;

import lombok.Data;

import java.util.List;

/**
 * @author selegant
 */
@Data
public class KettleResourceTree {

    private Object[] selectedKeys;
    private Object[] expandedKeys;
    private List<KettleResourceTree.Tree> trees;

    @Data
    public static class Tree{
        public String title;

        public String key;

        public List<KettleResourceTree.Tree> children;
    }
}
