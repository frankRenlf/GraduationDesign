# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

# coding:UTF-8

from PIL import Image as image


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    f_center = open("center_pp")

    center = []
    for line in f_center.readlines():
        lines = line.strip().split("\t")
        tmp = []
        for x in lines:
            tmp.append(int(float(x) * 256))
        center.append(tuple(tmp))
    f_center.close()

    fp = open("bc2.jpg", "rb")
    im = image.open(fp)
    # 新建一个图片
    m, n = im.size
    pic_new = image.new("RGB", (m, n))

    f_sub = open("sub_pp")
    i = 0
    for line in f_sub.readlines():
        index = float((line.strip().split("\t"))[0])
        index_n = int(index)
        pic_new.putpixel((int(i / n), (i % n)), center[index_n])
        i = i + 1
    f_sub.close()

    pic_new.save("result2.jpg", "JPEG")

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
