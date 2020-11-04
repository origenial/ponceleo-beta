(ns ponceleo.pages.error
  (:require [ponceleo.router :as prouter]))

(defn error-404 []
  [:div.p-8
    [:h1.text-3xl.text-bold "Erreur 404"]
    [:p "Désolé, nous n'avons pas trouvé la page que vous cherchiez."]
    [:a {:href (prouter/path-for :index)} "Retourner à l'accueil"]])
