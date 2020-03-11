package com.algo;

import java.util.*;

/*
二维平面上有n个点，如何快速计算出两个距离最近的点对
 */
public class ClosestPoint {
    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    //points按Point.x升序排好序了
    public double minDistance(int left, int right, Point[] points) {
        if (left == right) {
            return Double.MAX_VALUE;
        }
        if (left == right - 1) {
            return distance(points[left], points[right]);
        }
        int mid = left + ((right - left) >> 1);
        double x = minDistance(left, mid, points);
        double y = minDistance(mid + 1, right, points);
        //最小距离也可能是左右某二个点的距离
        double min = Math.min(x, y);
        List<Point> list = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - points[mid].x) < min) {
                list.add(points[i]);
            }
        }
        //根据纵坐标从小到大排序
        list.sort((p1, p2) -> {
            if (p1.y > p2.y) {
                return 1;
            } else if (p1.y == p2.y) {
                return 0;
            } else {
                return -1;
            }
        });
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j <list.size(); j++) {
                if (Math.abs(points[i].y - points[j].y) >= min) {
                    break;
                }
                double distance = distance(points[i], points[j]);
                if (min > distance) {
                    min = distance;
                }
            }
        }
        return min;
    }

    public double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    //暴力求解
    public double minDistance(Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                min = Math.min(min, distance(points[i], points[j]));
            }
        }
        return min;
    }

    double getMin(Point[] points, int low, int high) {
        if (high - low == 1)    //2个结点
        {
            return distance(points[low], points[low + 1]);
        } else if (high - low == 2)        //3个结点
        {
            double dist1 = distance(points[low], points[low + 1]);
            double dist2 = distance(points[low], points[low + 2]);
            double dist3 = distance(points[low + 1], points[low + 2]);
            return Math.min(Math.min(dist1, dist2), dist3);
        } else {
            int mid = (low + high) / 2;
            double left_min = getMin(points, low, mid);
            double right_min = getMin(points, mid + 1, high);
            double d = Math.min(left_min, right_min);
            List<Point> list = new ArrayList();
            int i, j;
            //遍历一遍数组，得到与横坐标与  中点横坐标距离在d以内的点
            for (i = low; i <= high; i++) {
                if (Math.abs(points[i].x - points[mid].x) < d)
                    list.add(points[i]);
            }
            //根据纵坐标从小到大排序
            list.sort((p1, p2) -> {
                if (p1.y > p2.y) {
                    return 1;
                } else if (p1.y == p2.y) {
                    return 0;
                } else {
                    return -1;
                }
            });
            for (i = 0; i < list.size() - 1; i++) {
                //求距离的点 为与i纵坐标的距离在d以内
                for (j = i + 1; j < list.size(); j++) {
                    if (list.get(j).y - list.get(i).y >= d)
                        break;
                    double dp = distance(list.get(i), list.get(j));
                    if (dp < d) {
                        d = dp;
                    }
                }
            }
            return d;
        }
    }

    public static void main(String[] args) {
        ClosestPoint closestPoint = new ClosestPoint();
        int count = 100000;
        Point[] points = new Point[count];
        Random random = new Random();
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(random.nextDouble() * random.nextInt(count), random.nextDouble() * random.nextInt(count));
        }
        Arrays.sort(points, (p1, p2) -> {
            if (p1.x > p2.x) {
                return 1;
            } else if (p1.x == p2.x) {
                return 0;
            } else {
                return -1;
            }
        });
        long start = System.currentTimeMillis();
        double v = closestPoint.getMin(points, 0, points.length - 1);
        System.out.println(v + " " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        v = closestPoint.minDistance(0, points.length - 1, points);
        System.out.println(v + " " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(closestPoint.minDistance(points) + " " + (System.currentTimeMillis() - start));
    }
}
