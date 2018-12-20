export class Page<T> {
  content?: [T];
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: string;
  numberOfElements?: number;
  first: boolean;

}
