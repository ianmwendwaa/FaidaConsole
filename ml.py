# from sklearn.cluster import DBSCAN
# import numpy as np

# coordinates = np.array([

#     [37.77, -122.42],

#     [37.78, -122.41],

#     [37.76, -122.43],

#     [37.74, -122.44],

#     [37.73, -122.45]

# ])

 

# db = DBSCAN(eps=0.01, min_samples=2).fit(coordinates)

# labels = db.labels_

# print(labels)

from sklearn.mixture import GaussianMixture

# import numpy as np

# # Generating sample data

# np.random.seed(0)

# data = np.random.randn(300, 2)

# # Fitting the Gaussian Mixture Model

# gmm = GaussianMixture(n_components=3, random_state=0).fit(data)

# labels = gmm.predict(data)

# print(np.unique(labels, return_counts=True))

from sklearn.decomposition import PCA

import matplotlib.pyplot as plt
import numpy as np

# Assuming X is the input data matrix

pca = PCA(n_components=2)
X = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9], [10, 11, 12]])

X_pca = pca.fit_transform(X)

plt.scatter(X_pca[:, 0], X_pca[:, 1])

plt.show()