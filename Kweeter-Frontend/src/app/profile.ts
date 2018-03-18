export interface IProfile {
  id: number;
  username: string;
  displayName: string;
  emailAddress: string;
  location: string;
  contactLink: string;
  bio: string;
  password: string;
}

export class Profile implements IProfile {
  id: number = this.profile.id;
  username: string = this.profile.username;
  displayName: string = this.profile.displayName;
  emailAddress: string = this.profile.emailAddress;
  location: string = this.profile.location;
  contactLink: string = this.profile.contactLink;
  bio: string = this.profile.bio;
  password: string = this.profile.password;

  constructor(public profile:IProfile) {}
}
