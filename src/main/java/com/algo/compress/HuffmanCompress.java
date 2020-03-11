package com.algo.compress;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCompress {
    static class HuffmanTree {
        private HuffNode root;

        HuffmanTree(HuffNode root) {
            this.root = root;
        }

        /*
        返回map为空表示source只有一种字符，需要特殊处理
         */
        Map<Byte, String> getHuffCode() {
            Map<Byte, String> map = new HashMap<>();
            if (root.left == null && root.right == null) {
                return map;
            }
            StringBuilder sb = new StringBuilder();
            buildHuffCode(root, map, sb);
            return map;
        }

        private void buildHuffCode(HuffNode root, Map<Byte, String> map, StringBuilder sb) {
            HuffNode p = root;
            if (p.left != null) {
                sb.append('0');
                buildHuffCode(p.left, map, sb);
            }
            if (p.right != null) {
                sb.append('1');
                buildHuffCode(p.right, map, sb);
            }
            if (p.left == null && p.right == null) {
                map.put(p.c, sb.toString());
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }

    static class HuffNode {
        byte c;
        int freq;
        HuffNode left;
        HuffNode right;

        HuffNode(byte c, int freq) {
            this.c = c;
            this.freq = freq;
        }

        HuffNode(int freq) {
            this.freq = freq;
        }
    }

    public static void main(String[] args) throws IOException {
        HuffmanCompress huffmanCompress = new HuffmanCompress();

//        PriorityQueue<HuffNode> queue = new PriorityQueue(6, Comparator.comparingInt((HuffNode h) -> h.freq));
//        queue.offer(new HuffNode((byte) 'a', 2));
//        queue.offer(new HuffNode((byte) 'b', 21));
//        queue.offer(new HuffNode((byte) 'c', 5));
//        queue.offer(new HuffNode((byte) 'd', 11));
//        queue.offer(new HuffNode((byte) 'e', 6));
//        queue.offer(new HuffNode((byte) 'f', 10));
//        HuffNode huffNode = huffmanCompress.buildHuffmanTree(queue);
//        Map<Byte, String> huffCode = new HuffmanTree(huffNode).getHuffCode();
        huffmanCompress.encode(new File("d://tmp/1.mp3"), new File("d://tmp/1.huff"));
//        huffmanCompress.decode(new File("d://tmp/1.huff"), new File("d://tmp/decode.log"));
    }

    public void decode(File input, File output) throws IOException {
        FileOutputStream out = new FileOutputStream(output);
        DataInputStream in = new DataInputStream(new FileInputStream(input));
        if (in.readByte() != 'c' || in.readByte() != 'h' || in.readByte() != 'x') {
            throw new RuntimeException("Can't decode file, the format is valid");
        }
        int fileSize = in.readInt();
        int charCount = in.readInt();
        if (charCount == 1) {
            byte b = in.readByte();
            while (fileSize-- > 0) {
                out.write(b);
            }
        } else {
            PriorityQueue<HuffNode> queue = new PriorityQueue(charCount, Comparator.comparingInt((HuffNode h) -> h.freq));
            int c = charCount;
            while (c-- > 0) {
                queue.offer(new HuffNode(in.readByte(), in.readInt()));
            }
            HuffNode root = buildHuffmanTree(queue);
            HuffNode temp = root;
            while (true) {
                byte b = in.readByte();
                String code = Integer.toUnsignedString(b, 2);
                if (b < 0) {
                    code = code.substring(code.length() - 8);
                } else if (code.length() < 8) {
                    int n = 8 - code.length();
                    while (n-- > 0) {
                        code = "0" + code;
                    }
                }
                for (int i = 0; i < code.length(); i++) {
                    if (code.charAt(i) == '0') {
                        temp = temp.left;
                    } else {
                        temp = temp.right;
                    }
                    if (temp.left == null && temp.right == null) {
                        out.write(temp.c);
                        temp = root;
                        fileSize--;
                        if (fileSize == 0) {
                            break;
                        }
                    }
                }
                if (fileSize == 0) {
                    break;
                }
            }
        }
        out.flush();
        out.close();
        in.close();
    }

    public void encode(File input, File output) throws IOException {
        FileInputStream in = new FileInputStream(input);
        FileOutputStream out = new FileOutputStream(output);
        byte[] buf = new byte[1024];
        int len;
        Map<Byte,Integer> bytes = new HashMap<>();
        int size = 0;
        while ((len = in.read(buf)) != -1) {
            size += len;
            for (int i = 0; i < len; i++) {
                byte b = buf[i];
                if (bytes.containsKey(b)) {
                    bytes.put(b, bytes.get(b) + 1);
                } else {
                    bytes.put(b, 1);
                }
            }
        }
        int c = bytes.size();
        DataOutputStream dos = new DataOutputStream(out);
        //协议标识3个字节
        dos.writeByte('c');
        dos.writeByte('h');
        dos.writeByte('x');
        dos.writeInt(size);//文件大小
        dos.writeInt(c);//字符种类
        if (c == 1) {
            dos.writeByte(buf[0]);//字符
        } else {
            PriorityQueue<HuffNode> queue = new PriorityQueue(c, Comparator.comparingInt((HuffNode h) -> h.freq));
            for (Map.Entry<Byte, Integer> entry : bytes.entrySet()) {
                queue.offer(new HuffNode(entry.getKey(), entry.getValue()));
            }
            HuffNode root = buildHuffmanTree(queue);
            HuffmanTree tree = new HuffmanTree(root);
            for (Map.Entry<Byte, Integer> entry : bytes.entrySet()) {
                dos.writeByte(entry.getKey());
                dos.writeInt(entry.getValue());
            }
            Map<Byte, String> huffCode = tree.getHuffCode();
            in.close();
            in = new FileInputStream(input);
            byte b;
            StringBuilder sb = new StringBuilder();
            while ((b = (byte) in.read()) != -1) {
                sb.append(huffCode.get(b));
                if (sb.length() >= 1024) {
                    writeData(dos, sb);
                }
            }
            writeData(dos, sb);
            int remain = sb.length();
            if (remain > 0) {
                int last = Integer.parseUnsignedInt(sb.toString(), 2);
                dos.writeByte(last << (8 - remain));
            }
        }
        dos.flush();
        dos.close();
        in.close();
    }

    private HuffNode buildHuffmanTree(PriorityQueue<HuffNode> queue) {
        HuffNode root = null;
        while (!queue.isEmpty()) {
            HuffNode n1 = queue.poll();
            if (!queue.isEmpty()) {
                HuffNode n2 = queue.poll();
                HuffNode node = new HuffNode(n1.freq + n2.freq);
                node.left = n1;
                node.right = n2;
                queue.offer(node);
            } else {
                root = n1;
                break;
            }
        }
        return root;
    }

    private void writeData(DataOutputStream dos, StringBuilder sb) throws IOException {
        int n = sb.length();
        int i = 0;
        while (n >= 8) {
            dos.writeByte(Integer.parseUnsignedInt(sb.substring(i, i + 8), 2));
            i += 8;
            n -= 8;
        }
        sb.delete(0, i);
    }
}
