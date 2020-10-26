(ns ponceleo.pages.home.index
  (:require
    [reagent.core :as reagent :refer [atom]]
    [ponceleo.pages.home.component.motto :refer [motto-section]]
    [ponceleo.pages.home.component.subscribe-form :refer [subscribe-section]]
    [ponceleo.pages.home.component.feature-card :refer [feature-card]]
    [ponceleo.pages.home.component.stat-card :refer [stat-card]]
    [ponceleo.pages.home.component.contact-form :refer [contact-form]]
    ))

(def landing
    [:div#landing {:class ["relative" "bg-header-image" "bg-cover" "bg-no-repeat" "h-3/4" "flex" "flex-col" "items-center"]}
             [:div {:class ["bg-black" "absolute" "w-full" "min-h-full" "bg-opacity-50"]}]
             [:div {:class ["container" "w-3/4" "relative" "my-auto" "flex" "flex-col" "xl:flex-row" "flex-initial"]}
              motto-section
              subscribe-section]])

(def features
  [:div#features {:class ["w-full" "py-8" "text-center"]}
        [:h2 {:class ["my-8" "font-bold" "text-3xl" "uppercase"]} "Nos fonctionnalités"]
        [:div {:class ["flex" "flex-wrap" "flex-row"]}
         (map feature-card [
                            {
                             :key                 "feature-documents"
                             :image-src           "img/documents-icon.svg",
                             :feature-caption     "Centralisez vos documents"
                             :feature-description '("Garanties commerciales, notices d'utilisation et factures, " [:strong "conservez sur Ponceleo tous les papiers de vos achats. "]
                                                     "Vous pourrez même " [:strong "programmer des rappels"] " pour être notifié avant l'expiration de vos garanties commerciales!")
                             }
                            {
                             :key                 "feature-maintenance"
                             :image-src           "img/maintenance-icon.svg",
                             :feature-caption     "Conseils et calendrier d'entretien"
                             :feature-description '([:strong "Augmentez la durée de vie de vos objets"] " en suivant le calendrier d'entretien que " [:strong "Ponceleo"] " vous a concocté. "
                                                    "Découvrez dans notre blog comment bichonner vos biens. "
                                                    [:strong "Plus d'entretien, moins de pépins!"])
                             }
                            {
                             :key                 "feature-partners"
                             :image-src           "img/partners-network-icon.svg",
                             :feature-caption     "Bénéficiez d'offres partenaires"
                             :feature-description '( "Bénéficiez d'offres commerciales chez notre réseau de partenaires associatifs et professionnels :  produits d'entretien, pièces de rechange, " [:strong "dépannage (près de) chez vous !"])
                             }
                            ])]])

(def stats
    [:div#stats {:class ["relative" "bg-stats-image" "bg-cover" "bg-no-repeat"  "bg-fixed"]}
         [:div {:class ["bg-gray-800" "absolute" "w-full" "min-h-full" "bg-opacity-50"]}]
         [:div {:class ["w-full" "relative" "my-8" "p-8" "flex" "flex-row" "flex-wrap" "text-white" "text-center"]}
          (map stat-card [{
                           :key                 "stat-subscribers",
                           :image-src           "img/enveloppe-icon.svg",
                           :image-alt           "Enveloppe icon",
                           :stat-number         50
                           :stat-description   "Abonnés à la newsletter"
                           }
                          {
                           :key                 "stat-per-day-visit",
                           :image-src           "img/eye-icon.svg",
                           :image-alt           "Eye icon",
                           :stat-number         30
                           :stat-description   "Visites par jour"
                           }
                          ])]])

(defn contact []
  (fn []
    [:div#contact {:class ["mx-8" "md:mx-auto" "md:w-1/2" "py-4" "text-center" "h-3/4"]}
      [:h2 {:class ["my-4" "font-bold" "text-3xl" "uppercase"]} "Nous contacter"]
      [contact-form]
      ]))

(defn home-page []
  (fn []
    [:div
     landing
     features
     stats
     [contact]
     ]))