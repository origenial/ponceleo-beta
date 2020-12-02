(ns ponceleo.landing.frontend.pages.error
  "This namespace gathers the definitions of many error pages's Reagent
  components"
  (:require
   [ponceleo.landing.frontend.router :as prouter]))

(defn error-404
  "Returns a custom 404 error reagent Hiccup styled component"
  []
  [:div.p-8
    [:h1.text-3xl.text-bold "Erreur 404"]
    [:p "Désolé, nous n'avons pas trouvé la page que vous cherchiez."]
    [:a {:href (prouter/path-for :index)} "Retourner à l'accueil"]])
