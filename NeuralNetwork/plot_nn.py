import matplotlib.pyplot as plt
import numpy as np

colors = ['gold', 'green', 'blue', 'purple']
def plot_classification(filename):
    x = []
    y = []
    c = []
    m = []
    iteration = 1
    with open(filename, 'r') as file:
        file_list = file.read().splitlines()
        while file_list:
            point = file_list.pop(0).split(' ')
            # color = 'blue' if point[2] == point[3] else 'red'
            x.append(float(point[0]))
            y.append(float(point[1]))
            c.append(colors[int(point[3]) - 1] if point[2] == point[3] else 'red')
            m.append('*' if point[2] == point[3] else 'x')
    x = np.array(x)
    y = np.array(y)
    c = np.array(c)
    m = np.array(m)
    fig, ax = plt.subplots()
    ax.scatter(x[m == '*'], y[m == '*'], c=c[m == '*'], s=2, marker='*')
    ax.scatter(x[m == 'x'], y[m == 'x'], c=c[m == 'x'], marker='x')

    # plt.title(f'{center_num} Clusters')
    plt.title(f'Neural Network Classification')
    plt.gca().get_xaxis().set_visible(False)
    plt.gca().get_yaxis().set_visible(False)
    plt.show()

def plot_loss(filename):
    x = []
    y = []
    with open(filename, 'r') as file:
        file_list = file.read().splitlines()
        while file_list:
            x.append(float(file_list.pop(0)))
        y = [i for i in range(len(x))]
    plt.plot(y, x)
    plt.title(f'Neural Network Loss')
    plt.xlabel("Epoch")
    plt.ylabel("Loss (MSE)")
    plt.show()

plot_classification('classification_result.txt')
plot_loss('classification_result_loss.txt')
