from functools import lru_cache

grid_sn = 4151


@lru_cache(maxsize=None)
def calculate_rectangle(x1, y1, x2, y2):
    half_x = (x2 - x1) // 2
    half_y = (y2 - y1) // 2

    if x1 == x2 and y1 == y2:
        return (((((x1 + 11) * (y1 + 1)) + grid_sn) * (x1 + 11) // 100) % 10) - 5
    elif x1 == x2:
        value = calculate_rectangle(x1, y1, x1, y1 + half_y)
        value += calculate_rectangle(x1, y1 + half_y + 1, x1, y2)
        return value
    elif y1 == y2:
        value = calculate_rectangle(x1, y1, x1 + half_x, y1)
        value += calculate_rectangle(x1 + half_x + 1, y1, x2, y1)
        return value

    value = calculate_rectangle(x1, y1, x1 + half_x, y1 + half_y)
    value += calculate_rectangle(x1 + half_x + 1, y1, x2, y1 + half_y)
    value += calculate_rectangle(x1, y1 + half_y + 1, x1 + half_x, y2)
    value += calculate_rectangle(x1 + half_x + 1, y1 + half_y + 1, x2, y2)
    return value


maximum = 0
x = 0
y = 0
size = 0
for i in range(0, 300):
    for j in range(0, 300):
        for k in range(0, 300 - max(i, j)):
            val = calculate_rectangle(i, j, i + k, j + k)
            if val > maximum:
                maximum = val
                x = i + 1
                y = j + 1
                size = k+1

print(str(x) + "," + str(y) + "," + str(size))
