package com.synechisveltiosi.apis.app365.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app365")
public class AppConfig {

    private NonProtectedEndpoints nonProtectedEndpoints;
    private Crm crm;

    public NonProtectedEndpoints getNonProtectedEndpoints() {
        return nonProtectedEndpoints;
    }

    public void setNonProtectedEndpoints(NonProtectedEndpoints nonProtectedEndpoints) {
        this.nonProtectedEndpoints = nonProtectedEndpoints;
    }

    public Crm getCrm() {
        return crm;
    }

    public void setCrm(Crm crm) {
        this.crm = crm;
    }

    public static class NonProtectedEndpoints {

        private String[] getEndpoints;
        private String[] postEndpoints;
        private String[] putEndpoints;

        public String[] getGetEndpoints() {
            return getEndpoints;
        }

        public void setGetEndpoints(String[] getEndpoints) {
            this.getEndpoints = getEndpoints;
        }

        public String[] getPostEndpoints() {
            return postEndpoints;
        }

        public void setPostEndpoints(String[] postEndpoints) {
            this.postEndpoints = postEndpoints;
        }

        public String[] getPutEndpoints() {
            return putEndpoints;
        }

        public void setPutEndpoints(String[] putEndpoints) {
            this.putEndpoints = putEndpoints;
        }
    }

    public static class Crm {
        private GetEndpoints getEndpoints;
        private PostEndpoints postEndpoints;
        private PutEndpoints putEndpoints;

        public GetEndpoints getGetEndpoints() {
            return getEndpoints;
        }

        public void setGetEndpoints(GetEndpoints getEndpoints) {
            this.getEndpoints = getEndpoints;
        }

        public PostEndpoints getPostEndpoints() {
            return postEndpoints;
        }

        public void setPostEndpoints(PostEndpoints postEndpoints) {
            this.postEndpoints = postEndpoints;
        }

        public PutEndpoints getPutEndpoints() {
            return putEndpoints;
        }

        public void setPutEndpoints(PutEndpoints putEndpoints) {
            this.putEndpoints = putEndpoints;
        }

        public static class GetEndpoints {
            private String cbaUser;
            private String associatedOrganisms;
            private AddressEndpoints address;
            private String place;
            private String precinct;
            private String militant;
            private String members;
            private String levels;
            private String electoralCollege;
            private String teamMembersEmailAddress;
            private String militantsEmailAddress;
            private String teamMembersPhoneNumber;
            private String militantsPhoneNumber;
            private String supportSource;
            private String associatedOrganissmBySupportSource;
            private String getAddress;

            public String getCbaUser() {
                return cbaUser;
            }

            public void setCbaUser(String cbaUser) {
                this.cbaUser = cbaUser;
            }

            public String getAssociatedOrganisms() {
                return associatedOrganisms;
            }

            public void setAssociatedOrganisms(String associatedOrganisms) {
                this.associatedOrganisms = associatedOrganisms;
            }

            public AddressEndpoints getAddress() {
                return address;
            }

            public void setAddress(AddressEndpoints address) {
                this.address = address;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getPrecinct() {
                return precinct;
            }

            public void setPrecinct(String precinct) {
                this.precinct = precinct;
            }

            public String getMilitant() {
                return militant;
            }

            public void setMilitant(String militant) {
                this.militant = militant;
            }

            public String getMembers() {
                return members;
            }

            public void setMembers(String members) {
                this.members = members;
            }

            public String getLevels() {
                return levels;
            }

            public void setLevels(String levels) {
                this.levels = levels;
            }

            public String getElectoralCollege() {
                return electoralCollege;
            }

            public void setElectoralCollege(String electoralCollege) {
                this.electoralCollege = electoralCollege;
            }

            public String getTeamMembersEmailAddress() {
                return teamMembersEmailAddress;
            }

            public void setTeamMembersEmailAddress(String teamMembersEmailAddress) {
                this.teamMembersEmailAddress = teamMembersEmailAddress;
            }

            public String getMilitantsEmailAddress() {
                return militantsEmailAddress;
            }

            public void setMilitantsEmailAddress(String militantsEmailAddress) {
                this.militantsEmailAddress = militantsEmailAddress;
            }

            public String getTeamMembersPhoneNumber() {
                return teamMembersPhoneNumber;
            }

            public void setTeamMembersPhoneNumber(String teamMembersPhoneNumber) {
                this.teamMembersPhoneNumber = teamMembersPhoneNumber;
            }

            public String getMilitantsPhoneNumber() {
                return militantsPhoneNumber;
            }

            public void setMilitantsPhoneNumber(String militantsPhoneNumber) {
                this.militantsPhoneNumber = militantsPhoneNumber;
            }

            public String getSupportSource() {
                return supportSource;
            }

            public void setSupportSource(String supportSource) {
                this.supportSource = supportSource;
            }

            public String getAssociatedOrganissmBySupportSource() {
                return associatedOrganissmBySupportSource;
            }

            public void setAssociatedOrganissmBySupportSource(String associatedOrganissmBySupportSource) {
                this.associatedOrganissmBySupportSource = associatedOrganissmBySupportSource;
            }

            public String getGetAddress() {
                return getAddress;
            }

            public void setGetAddress(String getAddress) {
                this.getAddress = getAddress;
            }

            public static class AddressEndpoints {
                private String countries;
                private String states;
                private String municipalities;
                private String cities;
                private String sections;
                private String sectors;
                private String districtByCountry;
                private String districtByState;
                private String statesByCountryAndDistrict;
                private String municipalitiesByDistrict;
                private String regionByMunicipalityAndDistrict;
                private String zoneByMunicipalityAndDistrict;
                private String zoneByMunicipalDistrict;
                private String zoneByRegion;

                public String getCountries() {
                    return countries;
                }

                public void setCountries(String countries) {
                    this.countries = countries;
                }

                public String getStates() {
                    return states;
                }

                public void setStates(String states) {
                    this.states = states;
                }

                public String getMunicipalities() {
                    return municipalities;
                }

                public void setMunicipalities(String municipalities) {
                    this.municipalities = municipalities;
                }

                public String getCities() {
                    return cities;
                }

                public void setCities(String cities) {
                    this.cities = cities;
                }

                public String getSections() {
                    return sections;
                }

                public void setSections(String sections) {
                    this.sections = sections;
                }

                public String getSectors() {
                    return sectors;
                }

                public void setSectors(String sectors) {
                    this.sectors = sectors;
                }

                public String getDistrictByCountry() {
                    return districtByCountry;
                }

                public void setDistrictByCountry(String districtByCountry) {
                    this.districtByCountry = districtByCountry;
                }

                public String getDistrictByState() {
                    return districtByState;
                }

                public void setDistrictByState(String districtByState) {
                    this.districtByState = districtByState;
                }

                public String getStatesByCountryAndDistrict() {
                    return statesByCountryAndDistrict;
                }

                public void setStatesByCountryAndDistrict(String statesByCountryAndDistrict) {
                    this.statesByCountryAndDistrict = statesByCountryAndDistrict;
                }

                public String getMunicipalitiesByDistrict() {
                    return municipalitiesByDistrict;
                }

                public void setMunicipalitiesByDistrict(String municipalitiesByDistrict) {
                    this.municipalitiesByDistrict = municipalitiesByDistrict;
                }

                public String getRegionByMunicipalityAndDistrict() {
                    return regionByMunicipalityAndDistrict;
                }

                public void setRegionByMunicipalityAndDistrict(String regionByMunicipalityAndDistrict) {
                    this.regionByMunicipalityAndDistrict = regionByMunicipalityAndDistrict;
                }

                public String getZoneByMunicipalityAndDistrict() {
                    return zoneByMunicipalityAndDistrict;
                }

                public void setZoneByMunicipalityAndDistrict(String zoneByMunicipalityAndDistrict) {
                    this.zoneByMunicipalityAndDistrict = zoneByMunicipalityAndDistrict;
                }

                public String getZoneByMunicipalDistrict() {
                    return zoneByMunicipalDistrict;
                }

                public void setZoneByMunicipalDistrict(String zoneByMunicipalDistrict) {
                    this.zoneByMunicipalDistrict = zoneByMunicipalDistrict;
                }

                public String getZoneByRegion() {
                    return zoneByRegion;
                }

                public void setZoneByRegion(String zoneByRegion) {
                    this.zoneByRegion = zoneByRegion;
                }
            }
        }

        public static class PostEndpoints {
            private String token;
            private String userAsMembers;
            private String members;
            private String militant;
            private String cbaHeader;
            private String addAddress;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getUserAsMembers() {
                return userAsMembers;
            }

            public void setUserAsMembers(String userAsMembers) {
                this.userAsMembers = userAsMembers;
            }

            public String getMembers() {
                return members;
            }

            public void setMembers(String members) {
                this.members = members;
            }

            public String getMilitant() {
                return militant;
            }

            public void setMilitant(String militant) {
                this.militant = militant;
            }

            public String getCbaHeader() {
                return cbaHeader;
            }

            public void setCbaHeader(String cbaHeader) {
                this.cbaHeader = cbaHeader;
            }

            public String getAddAddress() {
                return addAddress;
            }

            public void setAddAddress(String addAddress) {
                this.addAddress = addAddress;
            }
        }

        public static class PutEndpoints {
            private String address;
            private String members;
            private String militant;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMembers() {
                return members;
            }

            public void setMembers(String members) {
                this.members = members;
            }

            public String getMilitant() {
                return militant;
            }

            public void setMilitant(String militant) {
                this.militant = militant;
            }
        }
    }
}
