(ns ponceleo.pages.home.component.motto)

(def motto-section
  [:div {:class ["text-white" "text-center" "lg:mr-8" "mb-8" "xl:mb-0"]}
   [:h1 {:class ["text-5xl" "capitalize" "text-bold"]}
    "ponceleo"]
   [:p  "Bichonnez vos objets de valeur comme ils le méritent avec " [:strong "Ponceleo"] "." [:br]
    "Suivez le cycle de vie de vos objets : achat, carnet d'entretien, réparation, recyclage, revente et dons." [:br]
    "Et ça marche pour tous vos biens : électroménager, bijoux, outils de jardin et bien d'autres!"]])

