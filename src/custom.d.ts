/* eslint-disable @typescript-eslint/no-explicit-any */
declare module '*.svg' {
  const content: string & {
    ReactComponent: React.FunctionComponent<React.SVGProps<SVGSVGElement>>;
  };
  export default content;
}
