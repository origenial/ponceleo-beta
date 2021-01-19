(ns ponceleo.landing.frontend.pages.home.index
  "This namespace defines the home page of the SPA.
  See the `home_page` component to understand the page's structure "
  (:require
   [ponceleo.landing.frontend.pages.home.component.contact-form :refer [contact-form]]
   [ponceleo.landing.frontend.pages.home.component.feature-card :refer [feature-card]]
   [ponceleo.landing.frontend.pages.home.component.motto :refer [motto-section]]
   [ponceleo.landing.frontend.pages.home.component.stat-card :refer [stat-card]]
   [ponceleo.landing.frontend.pages.home.component.subscribe-form :refer
    [subscribe-section]]))

(defn landing
  "HTML Landing section of the website. This section gathers the Motto below
  the product's name and a tiny newsletter subscription form"
  []
  [:div#landing.relative
   [:div.overlay {:class ["bg-header-image" "bg-blurred-4" "bg-center"
                          "bg-scroll" "bg-cover"  "bg-no-repeat"]}]
   [:div.overlay {:class ["bg-black" "bg-opacity-50"]}]
   [:div {:class ["relative" "h-full" "flex" "flex-row" "flex-wrap"
                  "content-around" "justify-around" "px-4" "py-20" "md:px-12"]}
    [:div#motto {:class ["w-full" #_"xl:w-2/5"]} motto-section]
    [:div#subscribe {:class ["w-full" "md:w-3/4" "xl:w-2/5"]} [subscribe-section]]]])

(defn features
  "HTML stateless Section presenting the features of the product"
  []
  [:div#features {:class ["w-full" "py-8" "text-center"]}
   [:h2 {:class ["my-8" "font-bold" "text-3xl" "uppercase"]}
    "Nos fonctionnalités"]
   [:div {:class ["flex" "flex-wrap" "flex-row"]}
    (map feature-card
         [{:key "feature-documents"
           :image-src "/img/documents-icon.svg",
           :image-alt "Icone de documents",
           :feature-caption "Centralisez vos documents"
           :feature-description
           ["Garanties commerciales, notices d'utilisation et factures, "
            [:strong "conservez sur Ponceleo tous les papiers de vos achats. "]
            "Vous pourrez même " [:strong "programmer des rappels "]
            "pour être notifié avant l'expiration de vos garanties commerciales!"]}
          {:key "feature-maintenance"
           :image-src "/img/maintenance-icon.svg",
           :image-alt "Icône maintenance",
           :feature-caption "Conseils et calendrier d'entretien"
           :feature-description
           [[:strong "Augmentez la durée de vie de vos objets"]
            " en suivant le calendrier d'entretien que " [:strong "Ponceleo"]
            " vous a concocté. " "Découvrez dans notre blog comment bichonner vos
         biens. " [:strong "Plus d'entretien, moins de pépins!"]]}
          {:key "feature-partners"
           :image-src "/img/partners-network-icon.svg",
           :image-alt "Icône réseau de partenaires",
           :feature-caption "Bénéficiez d'offres partenaires"
           :feature-description
           ["Bénéficiez d'offres commerciales chez notre réseau de partenaires
        associatifs et professionnels :  produits d'entretien, pièces de
        rechange, " [:strong "dépannage (près de) chez vous !"]]}])]])

(defn stats
  "HTML stateless Section gathering some statistics about the product"
  []
  [:div#stats {:class ["relative" "h-full" "bg-stats-image" "bg-cover"
                       "bg-no-repeat" "bg-fixed"]}
   [:div {:class ["bg-gray-800" "absolute" "w-full"
                  "min-h-full" "bg-opacity-50"]}]
   [:div
    {:class ["w-full" "relative" "my-8" "p-8"
             "flex" "flex-row" "flex-wrap" "text-white" "text-center"]}
    (map stat-card
         [{:key "stat-subscribers",
           :image-src "img/enveloppe-icon.svg",
           :image-alt "Enveloppe icon",
           :stat-number 10
           :stat-description "Abonnés à la newsletter"
           }
          {:key "stat-per-day-visit",
           :image-src "img/eye-icon.svg",
           :image-alt "Eye icon",
           :stat-number 30
           :stat-description "Visites par jour"}])]])

(defn contact
  "HTML Section containing the contact form to send us an email"
  []
  (fn []
    [:div#contact
     {:class ["mx-8" "md:mx-auto" "md:w-1/2" "py-4" "text-center" "mb-24"]}
     [:h2 {:class ["my-4" "font-bold" "text-3xl" "uppercase"]} "Nous contacter"]
     [contact-form]]))

(defn home-page
  "HTML Home page containing a landing header, feature presentations,
  statisics and a contact form"
  []
  [:div#page
   [landing]
   [features]
   [stats]
   [contact]])
